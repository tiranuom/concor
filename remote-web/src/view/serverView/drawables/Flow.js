import React from 'react'

import Start from "./Start";
import SimpleTask from "./SimpleTask";
import SingleThreadedTask from "./SingleThreadedTask";
import SynchronizedRemoteTask from "./SynchronizedRemoteTask";
import ContinuationTask from "./ContinuationTask";
import SideEffect from "./SideEffect";
import Catch from "./Catch";
import End from "./End";

export default function ({yOffset, flow}) {

    var linkStartPoint = null;

    return <g transform={`translate(0, ${yOffset})`}>
        {flow.tasks.map((task, index) => {
            let xOffset = 100 + index * 160;

            switch (task.type) {
                case 'start':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 85;
                    return <g key={index}>
                        <Start flowId={flow.id} itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'simple':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <SimpleTask itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'single-threaded':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <SingleThreadedTask itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'synchronized-remote':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <SynchronizedRemoteTask itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'asynchronous-remote':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <ContinuationTask itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'side-effect':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <SideEffect itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'catch':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 110;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint - 10}/>
                        <Catch itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
                case 'end':
                    var lastLinkStartPoint = linkStartPoint;
                    linkStartPoint = xOffset + 100;
                    return <g key={index}>
                        <Arrow sx={lastLinkStartPoint} sy={35} ex={xOffset - lastLinkStartPoint + 20}/>
                        <End itemId={task.id} x={xOffset} y={0} task={task}/>
                    </g>;
            }
            return null;
        })}
    </g>
}

function Arrow({sx, sy, ex = 60, ey}) {
    return <g transform={`translate(${sx}, ${sy})`}>
        <path d={`M 7 7 L ${ex} 7`} fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
        <path d={`M ${ex + 5.88} 7 L ${ex - 1.12} 10.5 L ${ex + 0.63} 7 L ${ex - 1.12} 3.5 Z`} fill="#000000" stroke="#000000" strokeMiterlimit="10"
              pointerEvents="none"/>
    </g>
}