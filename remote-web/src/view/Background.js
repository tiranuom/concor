import React, {Component} from 'react'
import Canves from "./serverView/Canves";
import {connect} from 'react-redux'
import {clearError, getSchema, getStats} from "../actions/actions";
import {Alert, Col, Row} from "react-bootstrap";


class Background extends Component {

    componentDidMount() {
        this.props.loadSchema();
        setInterval(this.props.loadStats, 1000)
    }

    render() {
        let {flows, error, clearError} = this.props;

        if (!!error.length) {
            setTimeout(clearError, 2000)
        }

        return <Row>
            {!!error.length &&
            <Alert bsStyle={"danger"}>
                {error}
            </Alert>
            }
            <Col>
            {!!flows &&
            <Canves flows={flows}/>
            }
            </Col>
        </Row>
    }
}

const colorMapping = {
    'SINGLE_THREADED': 'red',
    'CACHED': 'green',
    'MULTI_THREADED': 'blue',
}

const mapStateToProps = (state) => {
    if (state.schema.status === "success") {
        var flows = Object.values(state.schema.data)
        flows.forEach(e => e.tasks = e.tasks || e.taskInfo);

        if (state.stats.status === "success") {
            flows.forEach(flow => {
                var taskFlows = state.stats.data.tasks;
                var flowTaskStat = taskFlows[flow.id]||[];
                var joinFlows = state.stats.data.joins;
                var flowJoinStat = joinFlows[flow.id] || [];
                flow.tasks.forEach(task => {
                    var taskFlowStat = flowTaskStat.find(e => e.id === task.id);
                    if (!!taskFlowStat) {
                        task.latency = taskFlowStat.latency
                    }
                    var taskJoinStat = flowJoinStat.find(e => e.taskId === task.id);
                    if (!!taskJoinStat) {
                        task.queue = {
                            color: colorMapping[taskJoinStat.joinType],
                            latency: taskJoinStat.latency,
                            queueSize: taskJoinStat.bufferSize
                        };
                    } else {
                        task.queue = null;
                    }
                })
            })
        }

        return ({
            flows,
            error: state.error
        });
    } else {
        return {
            error: state.error
        }
    }
};

const mapDispatchToProps = dispatch => ({
    loadSchema: () => dispatch(getSchema()),
    loadStats: () => dispatch(getStats()),
    clearError: () => dispatch(clearError())
})

export default connect(mapStateToProps, mapDispatchToProps)(Background)