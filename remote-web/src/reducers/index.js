import { combineReducers } from 'redux'
import schema from './schema'
import stats from './stats'

export default combineReducers({
    schema,
    stats
})