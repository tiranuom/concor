import { combineReducers } from 'redux'
import schema from './schema'
import stats from './stats'
import error from "./error";

export default combineReducers({
    schema,
    stats,
    error
})