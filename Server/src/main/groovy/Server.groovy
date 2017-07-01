import static spark.Spark.*;

class Server {

    static void main(String[] args) {
        get("/test", { req, res ->
            "caffeinate -u -t 2".execute()
            return "Hello World"
        })
    }
}
