import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("states", State.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/select", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("regions", Region.all());
      model.put("template", "templates/select.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post("/select", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   //redirect?
    // })
  }
}
