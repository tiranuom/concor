import React from 'react'
import Queue from "./Queue";
import JoinSelection from "./popover/JoinSelection";
import {toTime} from "../Canves";

export default function({x, y, task, itemId, queue, postQueue, onClick, onPostQueueClick, flowId}) {
    return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
        <rect x="4" y="30" width="224" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(97.5,-20)">
            <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica">{itemId}
            </text>
        </g>

        <g transform="translate(0,220)">
            <path d="M 0 0 L 128 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 4 -4 L 04 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 124 4 L 124 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <text x="60" y="20" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{toTime(task.latency)}
            </text>
        </g>

        <g transform="translate(0,220)">
            <path d="M 128 0 L 228 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 232 -4 L 232 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <path d="M 124 4 L 124 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
            <text x="180" y="20" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{toTime(task.secondaryLatency)}
            </text>
        </g>
        <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
        <g transform="translate(0,-30)">
            <path d="M 70 111.86 Q 101.75 129.6 133.5 111.86 Q 165.25 94.11 197 111.86 L 197 122.14 Q 165.25 104.4 133.5 122.14 Q 101.75 139.89 70 122.14 L 70 111.86 Z"
                  fill="#ffffff" stroke="#000000" strokeMiterlimit="10" transform="rotate(-90,133.5,117)"
                  pointerEvents="none"/>
            <rect x="123" y="175" width="20" height="10" fill="#ffffff" stroke="none" pointerEvents="none"/>
            <rect x="123" y="45" width="20" height="10" fill="#ffffff" stroke="none" pointerEvents="none"/>
        </g>
        {task.queue &&
        <Queue x={15} y={70} queue={task.queue}/>
        }
        <g transform="translate(0,30)">
            <JoinSelection id={task.id} flowId={flowId}/>
        </g>
        {postQueue &&
        <Queue x={155} y={70} queue={postQueue} type={'s'}/>
        }
        <g transform="translate(120,30)">
            <div onClick={onPostQueueClick && onPostQueueClick.bind(this)} style={{width: 110, height: 110, opacity: 0.1}}></div>
        </g>
    </g>

}