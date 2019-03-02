import { combineReducers } from 'redux'
import schema from './schema'
import stats from './stats'
import error from "./error";
import timeSeries from "./timeSeries";

export default combineReducers({
    schema,
    stats,
    error,
    timeSeries
})