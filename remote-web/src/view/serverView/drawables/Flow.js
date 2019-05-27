import React from 'react'

import Start from "./Start";
import SimpleTask from "./SimpleTask";
import SingleThreadedTask from "./SingleThreadedTask";
import SynchronizedRemoteTask from "./SynchronizedRemoteTask";
import ContinuationTask from "./ContinuationTask";
import SideEffect from "./SideEffect";
import Catch from "./Catch";
import End from "./End";
import {connect} from "react-redux";
import {toTime} from "../Canves";
import C3Chart from 'react-c3js';
import 'react-c3js/package'

export default function ({yOffset, flow}) {

    var linkStartPoint = null;

    var joins = [];

    var lastJoin = {};

    console.log(flow.timeSeries)

    let timeSeries = (flow.timeSeries||[]).map(a => {
        a.averageLatency = a.averageLatency || 0;
        a.averageTps = a.averageTps || 0;
        a.latency = a.latency || 0;
        return a;
    })

    return <g transform={`translate(0, ${yOffset})`}>
        {flow.tasks.map((task, index) => {
            let xOffset = 100 + index * 160;

            function appendJoin(start, end = start - 20) {
                if (!!task.queue) {
                    lastJoin.end = lastJoin.end || end;
                    lastJoin = {
                        join: task.queue,
                        start: start
                    };
                    joins.push(lastJoin)
                }
            }

            switch (task.type) {
                case 'start':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 85;

                    appendJoin(linkStartPoint - 20);
                    return <g key={index}>
                        <Start flowId={flow.id} itemId={task.id} x={xOffset} y={0} task={task} flow={flow}/>
                    </g>;
                case 'simple':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10} />
                        <SimpleTask itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'single-threaded':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10} />
                        <SingleThreadedTask itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'synchronized-remote':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10} />
                        <SynchronizedRemoteTask itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'asynchronous-remote':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    lastJoin.end = xOffset + 60
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10} />
                        <ContinuationTask itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'side-effect':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10} />
                        <SideEffect itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'error':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    appendJoin(linkStartPoint - 100);
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <Catch itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
                case 'end':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 100;
                    appendJoin(linkStartPoint - 100);
                    lastJoin.end = xOffset;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint + 20} />
                        <End itemId={task.id} x={xOffset} y={0} task={task} flowId={flow.id}/>
                    </g>;
            }
            return null;
        })}
        <g transform={`translate(0, ${yOffset + 40})`}>
            {joins.map(({start, end, join}) => {
                return <g>
                    <path d={`M ${start} 0 L ${end} 0`} fill={'none'} stroke={join.color} strokeMiterlimit="10" strokeWidth={1}/>
                    <text x={(start + end) / 2} y="20" fill="#000000" textAnchor="middle" fontSize="10px" fontFamily="Helvetica" alignmentBaseline={"central"}>{toTime(join.latency)}
                    </text>
                </g>
            })}

        </g>
        <g transform={`translate(50, ${yOffset + 60})`}>
            <foreignObject style={{width: 800, height: 300}}>
                <C3Chart
                    size={{
                        width: 800,
                        height: 300
                    }}
                    color={{
                        pattern: ['#c0c7e8', '#0a008a']
                    }}
                    data={{
                        x: 'x',
                        columns: [
                            ['x', ...(timeSeries).map(a => a.date)],
                            ['tps', ...(timeSeries).map(a => a.tps)],
                            ['averageTps', ...(timeSeries).map(a => a.averageTps)]
                        ],
                        type: 'bar',
                        types: {
                            averageTps: 'line'
                        }
                    }}
                    area={{
                        zerobased: true
                    }}
                    axis={{
                        x: {
                            type: 'timeseries',
                            tick: {
                                format: '%H:%M:%S',
                                rotate: 60
                            }
                        }
                    }}
                    grid={{
                        y: {
                            show: true
                        }
                    }}
                />
            </foreignObject>
        </g>
        <g transform={`translate(50, ${yOffset + 380})`}>
            <foreignObject style={{width: 800, height: 300}}>
                <C3Chart
                    size={{
                        width: 800,
                        height: 300
                    }}
                    color={{
                        pattern: ['#98b09b', '#005e0c']
                    }}
                    data={{
                        x: 'x',
                        columns: [
                            ['x', ...(timeSeries).map(a => a.date)],
                            ['latency', ...(timeSeries).map(a => a.latency / 1000000)],
                            ['averageLatency', ...(timeSeries).map(a => a.averageLatency / 1000000)]
                        ],
                        type: 'bar',
                        types: {
                            averageLatency: 'line'
                        }
                    }}
                    area={{
                        zerobased: true
                    }}
                    axis={{
                        x: {
                            type: 'timeseries',
                            tick: {
                                format: '%H:%M:%S',
                                rotate: 60
                            }
                        }
                    }}
                    grid={{
                        y: {
                            show: true
                        }
                    }}
                    transition={{
                        duration: null
                    }}
                />
            </foreignObject>
        </g>
    </g>
}

function Arrow({sx, sy, ex = 60, ey}) {
    return <g transform={`translate(${sx}, ${sy})`}>
        <path d={`M 7 7 L ${ex} 7`} fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
        <path d={`M ${ex + 5.88} 7 L ${ex - 1.12} 10.5 L ${ex + 0.63} 7 L ${ex - 1.12} 3.5 Z`} fill="#000000" stroke="#000000" strokeMiterlimit="10"
              pointerEvents="none"/>
    </g>
}