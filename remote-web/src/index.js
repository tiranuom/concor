import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

import { combineReducers, applyMiddleware, createStore} from 'redux'
import {Provider} from 'react-redux'
import reducer from './reducers/index'
import { apiMiddleware } from 'redux-api-middleware';
import logger from 'redux-logger'
import thunk from 'redux-thunk'
import 'c3/c3.css';

// const store = createStore(reducer, {}, applyMiddleware(thunk, apiMiddleware, logger));
const store = applyMiddleware(logger, thunk, apiMiddleware)(createStore)(reducer, {});

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
