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

    // get("/select", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("regions", Region.all());
    //   model.put("template", "templates/select.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    // post("/select", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   //redirect?
    // })

    get("/states", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String url = String.format("/states/%d/regions", Integer.parseInt(request.queryParams("stateId")));
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/states/:id/regions", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        State state = State.find(Integer.parseInt(request.params(":id")));
        model.put("state", state);
        model.put("stateRegions", state.getRegions());
        model.put("template", "templates/regionstemplate.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/states/:state_id/regions/:region_id/hikes", (request,response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      State state = State.find(Integer.parseInt(request.params(":state_id")));
      model.put("state", state);
      Region region = Region.find(Integer.parseInt(request.params(":region_id")));
      model.put("region", region);
      model.put("hikes", region.getHikes());
      model.put("template", "templates/hikes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add-hike", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int state = Integer.parseInt(request.queryParams("stateId"));
      int region = Integer.parseInt(request.queryParams("regionId"));
      Region newRegion = Region.find(region);
      String regionName = newRegion.getName();

      String hikeName = request.queryParams("hikeName");
      int hikeDifficulty = Integer.parseInt(request.queryParams("hikeDifficulty"));
      int hikeDistance = Integer.parseInt(request.queryParams("hikeDistance"));
      // String hikeDogsS = String.valueOf(request.queryParams("hikeDogs"));
      Boolean hikeDogs = true;
      Boolean hikeWaterfalls = true;
      // boolean hikeDogs = Boolean.valueOf(request.queryParams("hikeDogs"));
      // boolean hikeWaterfalls = Boolean.valueOf(request.queryParams("hikeWaterfalls"));
      int hikeElevation = Integer.parseInt(request.queryParams("hikeElevation"));
      Hike hike = new Hike(hikeName, regionName, hikeDifficulty, hikeDistance, hikeDogs, hikeWaterfalls, hikeElevation);
      hike.save();

      String url = String.format("/states/%d/regions/%d/hikes", state, region);

      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  } // END MAIN
} // END APP
