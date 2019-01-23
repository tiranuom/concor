import React from 'react'

import Flow from "./drawables/Flow";

export default function ({flows}) {

    return <div style={{padding: 5}}>
        <svg style={{width: '100%', height: 800, borderColor: 'red', borderStyle: 'solid', borderWidth: 1}}>
            <defs>
                <marker id="arrow" markerWidth="10" markerHeight="10" refX="0" refY="3" orient="auto" markerUnits="strokeWidth" viewBox="0 0 20 20">
                    <path d="M0,0 L0,6 L9,3 z" />
                </marker>
            </defs>
            {flows.map((flow, index) => <Flow flow={flow} yOffset={(index + 1) * 100} key={flow.id} />)}

            {/*<path d={lineFunction(lineData)} stroke={'black'} strokeWidth={2} fill={'none'} markerEnd={"url(#arrow)"}/>*/}
            {/*<foreignObject x={200} y={200} width={100} height={100}>
                <div>test</div>
            </foreignObject>*/}
            {/*<Start x={100} y={100} queueSize={12} latency={'50ns'}/>
            <SimpleTask x={250} y={100} queueSize={12} latency={'50ns'}/>
            <SingleThreadedTask x={400} y={100} queueSize={12} latency={'50ns'}/>
            <SynchronizedRemoteTask x={550} y={100} queueSize={12} latency={'50ns'}/>
            <ContinuationTask x={700} y={100} queueSize={12} latency={'50ns'}/>
            <SideEffect x={850} y={100} queueSize={12} latency={'50ns'}/>
            <End x={1000} y={100}/>*/}
        </svg>
    </div>
}