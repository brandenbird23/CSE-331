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

import React, { Component, createRef } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";
// @ts-ignore
import domtoimage from "dom-to-image";


// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import {Edge} from "./Edge";

interface AppState {
    text: Edge[];
}

class App extends Component<{}, AppState> { // <- {} means no props.
    // reference to the map element
    private mapRef = createRef<HTMLDivElement>();

    constructor(props: any) {
        super(props);
        this.state = {
            text: []
        };
    }

    // download of just the map with the lines as a png
    downloadPNG() {
        // download the map to png, if it does not exist, return
        if (!(this.mapRef.current)) {
            return;
        } else {
            const node = this.mapRef.current;

            domtoimage.toPng(node, {
                // set height and width to that of the map div
                width: node.clientWidth,
                height: node.clientHeight,
            })
                .then(function (dataUrl: string) {
                    // create html element
                    const link = document.createElement("a");
                    link.download = "UW Lines Map.png";   // file name will be downloaded as
                    link.href = dataUrl;
                    link.click();
                })
                .catch(function (error: any) {
                    console.error("Error while generating PNG:", error);
                });
        }
    }

    // download for the full page to have the edges and map included as a pdf
    downloadPagePDF() {
        window.print()
    }

    render() {
        return (
            <div>
                <h1 id="app-title">UW Campus Path</h1>
                <div ref={this.mapRef}>
                    <Map input={this.state.text}/>
                </div>
                <EdgeList
                    onChange={(value) => {
                        this.setState({
                            text: value,
                        });
                    }}
                />
                <div id="reset" style = {{display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                    }}>
                    <button onClick={() => this.downloadPNG()}>Download Map PNG</button>
                    <button onClick={() => this.downloadPagePDF()}>Download Page PDF</button>
                </div>
            </div>
        );
    }
}

export default App;
