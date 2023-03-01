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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import {Edge} from "./Edge";

interface AppState {
    text: Edge[];
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
      text: []
    };
  }

    refreshPage() {
        window.location.reload();
    }

  render() {
      return (
          <div>
              <h1 id="app-title">UW Campus Path</h1>
              <div>
                  <Map
                      input = {this.state.text}
                  />
              </div>
              <EdgeList
                  onChange={(value) => {
                      this.setState({
                          text: value
                      });
                  }}
              />
              <div id = "reset" style = {{display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center'
              }}>
                  <button onClick={this.refreshPage}>Refresh</button>
              </div>
          </div>
      );
  }
}

export default App;
