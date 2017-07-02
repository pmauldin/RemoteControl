import org.eclipse.jetty.websocket.api.annotations.*
import org.eclipse.jetty.websocket.api.*

@SuppressWarnings("GrMethodMayBeStatic")
@WebSocket
class ServerEchoWebSocket {
    private static Session session

    @OnWebSocketConnect
    void connected(Session session) {
        println "Session opened"
        this.session = session
    }

    @OnWebSocketClose
    void closed(Session session, int statusCode, String reason) {
        println "Session closed"
        this.session = null
    }

    @OnWebSocketMessage
    void message(Session session, String message) {
        println "Got ${message} from ${session.remoteAddress}"
        session.getRemote().sendString(message)
    }

    static void relayMessage() {
        if(!session) return
        println "Sending caffeinate message"
        session.getRemote().sendString("wake")
    }

}
