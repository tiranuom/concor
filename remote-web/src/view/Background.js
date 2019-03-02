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

        if (error && !!error.length) {
            setTimeout(clearError, 2000)
        }

        return <Row>
            {error && !!error.length &&
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
                var flowTaskStats = taskFlows[flow.id]||[];
                var joinFlows = state.stats.data.joins;
                var flowJoinStats = joinFlows[flow.id] || [];

                flow.tasks.forEach(task => {
                    var taskStat = flowTaskStats.find(e => e.id === task.id);
                    if (!!taskStat) {
                        task.latency = taskStat.latency
                    }
                    var joinStat = flowJoinStats.find(e => e.taskId === task.id);
                    if (!!joinStat) {
                        task.queue = {
                            color: colorMapping[joinStat.joinType],
                            latency: joinStat.latency,
                            queueSize: joinStat.bufferSize
                        };
                    } else {
                        task.queue = null;
                    }

                    var secondaryTaskJoinStat = flowTaskStats.find(e => e.id === task.id + "/Resp" )
                    if (!!secondaryTaskJoinStat) {
                        task.secondaryLatency = secondaryTaskJoinStat.latency;
                    }
                });

                var tps = state.stats.data.tps;
                flow.tps = tps[flow.id];
                flow.latency = flowTaskStats.map(a=> a.latency).reduce((a, b) => a + b, 0);
                flow.timeSeries = state.timeSeries.map(a => ({
                    date: a.date,
                    tps: a.tps[flow.id],
                    latency: (a.tasks[flow.id] || []).map(a=> a.latency).reduce((a, b) => a + b, 0)
                }));
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