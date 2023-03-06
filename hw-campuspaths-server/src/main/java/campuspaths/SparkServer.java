/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        CampusMap map = new CampusMap();
        Spark.get("/route-paths", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startBuilding = request.queryParams("start");
                String endBuilding = request.queryParams("end");
                Path<Point> shortestPath = null;
                // half execution to return response to client if start or end is null
                if (startBuilding == null || endBuilding == null) {
                    Spark.halt(400, "Must have Start and End");
                } else if (!(map.shortNameExists(startBuilding))) {
                    Spark.halt(400, "Start Building must exist");
                } else if (!(map.shortNameExists(endBuilding))) {
                    Spark.halt(400, "End Building must exist");
                } else {
                    // trys to create the shortest path using Dijkstra's Algorithm
                    try {
                        shortestPath = map.findShortestPath(startBuilding, endBuilding);
                    } catch (IllegalArgumentException e) {
                        Spark.halt(400, "Unable to find path");
                    }
                }
                Gson gson = new Gson();
                return gson.toJson(shortestPath);
            }
        });

        Spark.get("/buildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = map.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildings.keySet());
            }
        });
    }

}
