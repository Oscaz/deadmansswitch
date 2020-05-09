package dev.oscaz.deadmansswitch.web;

import dev.oscaz.deadmansswitch.socket.DeadSocketHandler;
import dev.oscaz.deadmansswitch.socket.MessageHandler;
import spark.ModelAndView;
import spark.Route;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebServer {

    private static final VelocityTemplateEngine TEMPLATE_ENGINE = new VelocityTemplateEngine();
    private static final Map<String, Object> EMPTY_MAP = new HashMap<>();
    private static final Map<String, String> STATIC_CACHE = new HashMap<>();

    // Initialize webserver method
    public static void initialize() {
        Spark.port(9834);
        Spark.staticFileLocation("/public");

        Spark.notFound(staticResolve("404.html"));

        Spark.webSocket("/switch-actions", DeadSocketHandler.class);

        Spark.init();
    }

    // A set of utility methods to resolve a path to a page and merging with Velocity default template (maven dependency)

    public  static Route resolve(String contentPath) {
        return (request, response) -> render(null, "default.vtl", contentPath);
    }

    private static String render(Map<String, Object> model, String templatePath, String contentPath) {
        if (model == null) {
            model = new HashMap<>();
        }
        model.put("content", "/pages/" + contentPath);
        return TEMPLATE_ENGINE.render(new ModelAndView(model, "/pages/template/" + templatePath));
    }

    private static Route staticResolve(String path) {
        return (request, response) -> staticRender(path);
    }

    private static String staticRender(String path) {
        return STATIC_CACHE.computeIfAbsent(path, WebServer::computeStaticRender);
    }

    private static String computeStaticRender(String path) {
        return renderWithoutTemplate(EMPTY_MAP, "/public/" + path);
    }

    private static String renderWithoutTemplate(Map<String, Object> model, String templatePath) {
        return TEMPLATE_ENGINE.render(new ModelAndView(model, templatePath));
    }

    private WebServer() {
        throw new IllegalStateException("Cannot be instantiated.");
    }

}
