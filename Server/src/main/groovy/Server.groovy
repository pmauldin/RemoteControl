import static spark.Spark.*;

class Server {

    static void main(String[] args) {
        get("/test", { req, res ->
            return "Hello World"
        })
    }
}
