import React from 'react'
import Queue from "./Queue";
import JoinSelection from "./popover/JoinSelection";
import {toTime} from "../Canves";

export default function({x, y, task, itemId, queue, flowId}) {
    return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
        <rect x="4" y="30" width="224" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(97.5,-20) rotate(-10)">
            <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica">{itemId}
            </text>
        </g>

        <g transform="translate(0,220)">
            <path d="M 0 0 L 228 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 4 -4 L 04 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 224 4 L 224 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <text x="120" y="20" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{toTime(task.latency)}
            </text>
        </g>
        <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(-30,-30)">
            <path d="M 0 20 L 18 40 L 290 40 L 310 20" fill="none" stroke="#000000" strokeMiterlimit="10"
                  pointerEvents="none"/>
            <path d="M 0 210 L 20 190 L 290 190 L 310 210" fill="none" stroke="#000000" strokeMiterlimit="10"
                  pointerEvents="none"/>
        </g>
        {!!task.queue &&
        <Queue x={15} y={70} queue={task.queue}/>
        }
        <g transform="translate(0,30)">
            <JoinSelection id={task.id} flowId={flowId}/>
        </g>
    </g>

}