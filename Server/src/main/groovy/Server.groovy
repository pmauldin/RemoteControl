import groovy.transform.CompileStatic

import static spark.Spark.*

@CompileStatic
class Server {

    static void main(String[] args) {
        webSocket("/echo", ServerEchoWebSocket.class)

        get("/caffeinate", { req, res ->
            ServerEchoWebSocket.relayMessage()
            return "sent"
        })
    }
}
