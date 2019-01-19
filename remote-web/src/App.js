import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Background from './view/Background'
import { createStore } from 'redux'
import { Provider } from 'react-redux'

// const store = createStore({});

class App extends Component {
  render() {
    return (
      <div className="App">
        <Background/>
      </div>
    );
  }
}

export default App;
