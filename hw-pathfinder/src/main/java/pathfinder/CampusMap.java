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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CampusMap is a representation of the buildings and path of the
 * University of Washington. A user can get the data of a building and
 * find the shortest path between two buildings in the map.
 */
public class CampusMap implements ModelAPI {
    private Map<String, String> buildingNames;
    private Map<String, Point> buildCords;
    private Graph<Point, Double> campusGraph;


    /**
     * commenting soon
     */
    public CampusMap() {
        this.campusGraph = new Graph<>();
        this.buildingNames = new HashMap<>();
        this.buildCords = new HashMap<>();
        // Load data
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        // loop over building data to store them
        for (CampusBuilding building : buildings) {
            Point bCoordinates = new Point(building.getX(), building.getY());
            this.buildingNames.put(building.getShortName(), building.getLongName());
            this.buildCords.put(building.getShortName(), bCoordinates);
            this.campusGraph.addNode(bCoordinates);
        }
        // loop over path data to store them
        for (CampusPath path : paths) {
            Point start = new Point(path.getX1(), path.getY1());
            Point dest = new Point(path.getX2(), path.getY2());
            if(!(this.campusGraph.containsNode(start))) {
                this.campusGraph.addNode(start);
            }
            if (!(this.campusGraph.containsNode(dest))) {
                this.campusGraph.addNode(dest);
            }
            this.campusGraph.addEdge(start, dest, path.getDistance());
        }
    }

    /**
     *
     * @param shortName The short name of a building to query.
     * @return true if contains shortName
     */
    @Override
    public boolean shortNameExists(String shortName) {
        return this.buildingNames.containsKey(shortName);
    }

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building matched with the given shortName
     * @throws IllegalArgumentException if shortName does not exist
     */
    @Override
    public String longNameForShort(String shortName) {
        if (!(shortNameExists(shortName))) {
            throw new IllegalArgumentException("shortName does not exist");
        } else {
            return this.buildingNames.get(shortName);
        }
    }

    /**
     * @return a map of all buildings with shortName keys and longName values
     */
    @Override
    public Map<String, String> buildingNames() {
        return new HashMap<>(this.buildingNames);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null) {
            throw new IllegalArgumentException("startShortName cannot be null");
        } else if (endShortName == null) {
            throw new IllegalArgumentException("endShortName cannot be null");
        } else if (!(shortNameExists(startShortName))) {
            throw new IllegalArgumentException("Given startShortName does not exist");
        } else if (!(shortNameExists(endShortName))) {
            throw new IllegalArgumentException("Given endShortName does not exist");
        } else if (!(shortNameExists(startShortName)) && (!(shortNameExists(endShortName)))) {
            throw new IllegalArgumentException("Given names do not exist");
        } else {
            return Dijkstra.findPath(this.campusGraph, buildCords.get(startShortName), buildCords.get(endShortName));
        }
    }
}
