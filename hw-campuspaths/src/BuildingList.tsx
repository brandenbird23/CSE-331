/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/**
 * Large chunk of code from EdgeList.tsx in hw-lines
 */

import React, {Component} from 'react';
import {Edge} from "./Edge";

interface BuildingListProps {
    onChange(edges: Edge[]): void;  // called when a new edge list is ready

}

interface BuildingListState {
    startBuilding: string;
    endBuilding: string;
    buildings: [];
    color: string;
}


/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class BuildingList extends Component<BuildingListProps, BuildingListState> {

    constructor(props: BuildingListProps) {
        super(props);
        this.state = {
            startBuilding: "",
            endBuilding: "",
            color: "",
            buildings: [],
        }
        this.componentDidMount();
    }

    // retrieve building data from the server
    networkRequest = async () => {
        try {
            let response = await fetch("http://localhost:4567/buildings");
            if (!(response.ok)) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                // stop executing once response is bad
                return;
            } else {
                let data = await response.json();
                this.setState({
                    buildings: data,
                })
            }
            } catch (e) {
                alert("There was an error while trying to contact the server");
        }
    }

    // method to call for the networkRequest
    componentDidMount() {
        this.networkRequest();
    }

    clearText() {
        this.setState({
            startBuilding: "",
            endBuilding: "",
            color: "",
        })
        this.props.onChange([]);
    }

     changeText = async () => {
        const start = this.state.startBuilding;
        const end = this.state.endBuilding;
        const color = this.state.color;
        if (color === "") {
            alert("Please select a color");
            return;
        } else if (start === "") {
            alert("Please select a start building");
            return;
        } else if (end === "") {
            alert("Please select a end building");
            return;
        } else if (start === end) {
            alert("The start building and end building can't be the same");
            return;
        } else if (start === "" && end === "") {
            alert("Both the start building and end building must be selected");
        } else {
            try {
                let url = fetch("http://localhost:4567/route-paths?start=" + start + "&end=" + end);
                const response = await url;
                if (!(response.ok)) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    // stop executing once response is bad
                    return;
                } else {
                    const text = await response.text();
                    const edge = this.parsePath(text);
                    this.props.onChange(edge);
                }
            } catch (e) {
                alert("There was an error while trying to contact the server");
            }
        }
    }

    parsePath(data: string) {
        let ret: Edge[] = [];
        let obj = JSON.parse(data);
        let color: string;
        obj["path"].forEach((e: any) => {
            let edge: Edge = {
                x1: e['start']['x'],
                y1: e['start']['y'],
                x2: e['end']['x'],
                y2: e['end']['y'],
                color: color,
            }
            ret.push(edge);
        })
        return ret;
    }

    setStartBuilding (val: string) {
        this.setState({
            startBuilding: val,
        })
        console.log("Start Building: " + val)
    }

    setEndBuilding (val: string) {
        this.setState({
            endBuilding: val,
        })
        console.log("End Building: " + val)
    }

    setColor (val: string) {
        this.setState({
            color: val,
        })
        console.log("Color: " + val)
    }

    // refresh the page
    refreshPage() {
        window.location.reload();
    }


    render() {
        return (
            <div id={"building-list"}>
                <div>
                    <h3>Start Building</h3>
                    <div id="dropdown">
                        <select value={this.state.startBuilding} onChange={(e) => this.setStartBuilding(e.target.value)}>
                            <option value="" key=""></option>
                            {this.state.buildings.map((building) => (
                                <option value={building} key={building}>
                                    {building}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>
                <div>
                    <h3>End Building</h3>
                    <div id="dropdown">
                        <select value={this.state.endBuilding} onChange={(e) => this.setEndBuilding(e.target.value)}>
                            <option value="" key=""></option>
                            {this.state.buildings.map((building) => (
                                <option value={building} key={building}>
                                    {building}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>
                <div>
                    <h3>Color</h3>
                    <div id="dropdown">
                        <select value={this.state.color} onChange={(e) => this.setColor(e.target.value)}>
                            <option value="" key=""></option>
                            <option value="red" key="red">Red</option>
                            <option value="green" key="green">Green</option>
                            <option value="blue" key="blue">Blue</option>
                            <option value="yellow" key="yellow">Yellow</option>
                            <option value="purple" key="purple">Purple</option>
                            <option value="orange" key="orange">Orange</option>
                            <option value="black" key="black">Black</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button onClick={() => this.changeText()}>Find Path</button>
                    <button onClick={() => this.clearText()}>Clear</button>
                    <button onClick={() => this.refreshPage()}>Refresh</button>
                </div>
            </div>
        );
    }
}

export default BuildingList;