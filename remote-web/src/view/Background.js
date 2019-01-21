import React, {Component} from 'react'
import Canves from "./serverView/Canves";
import {connect} from 'react-redux'
import {getSchema, getStats} from "../actions/actions";

class Background extends Component {

    componentDidMount() {
        this.props.loadSchema();
        setInterval(this.props.loadStats, 1000)
    }

    render() {
        let {flows} = this.props;
        return <div>
            {!!flows &&
            <Canves flows={flows}/>
            }
        </div>
    }
}

const mapStateToProps = (state) => {
    if (state.schema.status === "success") {
        var flows = Object.values(state.schema.data)
        flows.forEach(e => e.tasks = e.tasks || e.taskInfo);

        if (state.stats.status === "success") {
            flows.forEach(flow => {
                var flows = state.stats.data.tasks;
                var flowStat = flows[flow.id]||[];
                flow.tasks.forEach(task => {
                    var taskStat = flowStat.find(e => e.id === task.id);
                    if (!!taskStat) {
                        task.latency = taskStat.latency
                    }
                })
            })
        }

        return ({
            flows
        });
    } else {
        return {}
    }
};

const mapDispatchToProps = dispatch => ({
    loadSchema: () => dispatch(getSchema()),
    loadStats: () => dispatch(getStats())
})

export default connect(mapStateToProps, mapDispatchToProps)(Background)