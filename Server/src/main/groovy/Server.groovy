import static spark.Spark.*

class Server {

    static void main(String[] args) {
        webSocket("/desktop", WebSocket.class)

        post("/caffeinate", { request, response ->
            def user = request.queryParams("user")
            def computerName = request.queryParams("computerName")

            WebSocket.relayMessage(user, computerName)

            return "ok"
        })
    }
}
