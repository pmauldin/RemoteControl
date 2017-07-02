import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import messages.ConnectionMessage
import messages.BaseMessage
import org.eclipse.jetty.websocket.api.annotations.*
import org.eclipse.jetty.websocket.api.*

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

@SuppressWarnings("GrMethodMayBeStatic")
@org.eclipse.jetty.websocket.api.annotations.WebSocket
class WebSocket {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>()
    private static final Map<String, Map<String, Session>> userConnectionsMap = new ConcurrentHashMap<>()

    private static final Gson gson = new Gson()

    @OnWebSocketConnect
    void connected(Session session) {
        println "Session opened"
        sessions.add(session)
    }

    @OnWebSocketClose
    void closed(Session session, int statusCode, String reason) {
        println "Session closed"
        sessions.remove(session)
    }

    @OnWebSocketMessage
    void message(Session session, String message) {
        def connection = null

        try {
            def clientMessage = gson.fromJson(message, BaseMessage)
            println "Got message of type ${clientMessage.type}"

            if (clientMessage.type == "connection") {
                connection = gson.fromJson(clientMessage.content, ConnectionMessage)
                println "New connection for ${connection.user} from ${connection.computerName}"
            }
        } catch(JsonSyntaxException ignored) {
            println "Unable to parse message ${message}"
            return
        }

        def userConnections = userConnectionsMap.get(connection.user)

        if (!userConnections) {
            userConnections = new ConcurrentHashMap<String, Session>()
            userConnectionsMap.put(connection.user, userConnections)
        }

        userConnections.put(connection.computerName, session)
    }

    static void relayMessage(String user, String computerName) {
        def userConnections = userConnectionsMap.get(user)
        if (!userConnections) {
            println "User $user has no active connections."
            return
        }

        def session = userConnections.get(computerName)
        if (!session) {
            println "No session for user $user and computer $computerName"
            return
        }

        session.getRemote().sendString("wake")
        println "Send wake signal to ${user}/${computerName}"
    }

}
