import React from 'react'
import Background from "../Background";
import * as d3 from 'd3'
import Start from "./drawables/Start";
import SimpleTask from "./drawables/SimpleTask";
import SingleThreadedTask from "./drawables/SingleThreadedTask";
import SynchronizedRemoteTask from "./drawables/SynchronizedRemoteTask";
import ContinuationTask from "./drawables/ContinuationTask";
import SideEffect from "./drawables/SideEffect";
import End from "./drawables/End";

export default function () {

    let lineData = [ { "x": 1,   "y": 5},  { "x": 20,  "y": 20},
        { "x": 40,  "y": 10}, { "x": 60,  "y": 40},
        { "x": 80,  "y": 5},  { "x": 100, "y": 60}];

    let lineFunction = d3.line()
        .x(function(d) { return d.x; })
        .y(function(d) { return d.y; })
        .curve(d3.curveBasis);

    return <div style={{padding: 5}}>
        <svg style={{width: '100%', height: 800, borderColor: 'red', borderStyle: 'solid', borderWidth: 1}}>
            <defs>
                <marker id="arrow" markerWidth="10" markerHeight="10" refX="0" refY="3" orient="auto" markerUnits="strokeWidth" viewBox="0 0 20 20">
                    <path d="M0,0 L0,6 L9,3 z" />
                </marker>
            </defs>
            {/*<path d={lineFunction(lineData)} stroke={'black'} strokeWidth={2} fill={'none'} markerEnd={"url(#arrow)"}/>*/}
            {/*<foreignObject x={200} y={200} width={100} height={100}>
                <div>test</div>
            </foreignObject>*/}
            <Start x={100} y={100} queueSize={12} latency={'50ns'}/>
            <SimpleTask x={250} y={100} queueSize={12} latency={'50ns'}/>
            <SingleThreadedTask x={400} y={100} queueSize={12} latency={'50ns'}/>
            <SynchronizedRemoteTask x={550} y={100} queueSize={12} latency={'50ns'}/>
            <ContinuationTask x={700} y={100} queueSize={12} latency={'50ns'}/>
            <SideEffect x={850} y={100} queueSize={12} latency={'50ns'}/>
            <End x={1000} y={100}/>
        </svg>
    </div>
}