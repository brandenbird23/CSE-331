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

import React, {Component} from 'react';
import {Edge} from "./Edge";

interface EdgeListProps {
    onChange(edges: Edge[]): void;  // called when a new edge list is ready

}

interface EdgeListState {
    text: string;
}


/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            text: ""
        }
    }

    clearText() {
        this.setState({
            text: "",
        })
        this.props.onChange([]);
    }

    changeText() {
        let edges = this.checkValidEdge(this.state.text);
        if (edges === null || edges.length === 0) {
            alert("Invalid input. Please input valid coordinates");
        } else {
            this.props.onChange(edges);
        }
    }

    checkValidEdge(text: string) {
        // edges from user given data
        let temp: Edge[] = [];
        let data: string[] = text.split("\n");

        // loop through each string in the array
        for (let i = 0; i < data.length; i++) {
            let flagger: boolean = true;
            let total = data[i].trim().split(" ");
            // check if string contains coordinates + color
            if (total.length < 5) {
                return null;
            }
            // loop through coordinates
            for (let j = 0; j < 4; j++) {
                if (isNaN(parseInt(total[j]))) {
                    alert("Invalid coordinates. Must be integers");
                    flagger = false;
                }
                if (parseInt(total[j]) < 0 || parseInt(total[j]) > 4000) {
                    alert("Invalid coordinates. Must be between 0 and 4000");
                    flagger = false;
                }
            }
            // coordinates are valid, create new Edge
            if (flagger) {
                let edge: Edge = {
                    x1: parseInt(total[0]),
                    y1: parseInt(total[1]),
                    x2: parseInt(total[2]),
                    y2: parseInt(total[3]),
                    color: total[4]
                }
                temp.push(edge);
            } else {
                return null;
            }
        }
        return temp;
    }

    // refresh the page
    refreshPage() {
        window.location.reload();
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(event) => this.setState({text: event.target.value})}
                    value={this.state.text}
                /> <br/>
                <button onClick={() => this.changeText()}>Draw</button>
                <button onClick={() => this.clearText()}>Clear</button>
                <button onClick={() => this.refreshPage()}>Refresh</button>
            </div>
        );
    }
}

export default EdgeList;
