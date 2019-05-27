import React from 'react'
import Queue from "./Queue";
import JoinSelection from "./popover/JoinSelection";
import {toTime} from "../Canves";

export default function({x, y, itemId, onClick, task, flowId}) {

    return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
        <rect x="4" y="30" width="224" height="110" fill="#ffffff" stroke="#ff0000" pointerEvents="none"/>
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
        <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#ff0000" pointerEvents="none"/>
        <g transform="translate(100,35)">
            <mask id={'filter-mask'}>
                <rect x="0" y="0" width="100" height="100" fill="black" />
                <ellipse cx="50" cy="50" rx="20" ry="45" fill="white" stroke="#000000" pointerEvents="none"/>
            </mask>
            <ellipse cx="50" cy="50" rx="20" ry="45" fill="#ffffff" stroke="#ff0000" pointerEvents="none" mask={"url(#filter-mask)"}/>
            <ellipse cx="50" cy="50" rx="22" ry="47" fill="#ffffff" stroke="#ff0000" pointerEvents="none"/>

            {
                [...Array(8)].map((_, i) => i * 12).map(i =>
                    <path d={`M 0 ${i} L 100 ${i + 1}`} fill="#ff0000" stroke="#ff0000" strokeMiterlimit="10" pointerEvents="none" mask={"url(#filter-mask)"}/>
                )
            }
            {
                [...Array(8)].map((_, i) => i * 12).map(i =>
                    <path d={`M ${i} 0 L ${i + 1} 100 `} fill="#ff0000" stroke="#ff0000" strokeMiterlimit="10" pointerEvents="none" mask={"url(#filter-mask)"}/>
                )
            }
        </g>

        {!!task.queue &&
        <Queue x={15} y={70} queue={task.queue}/>
        }
        <g transform="translate(0,30)">
            <JoinSelection id={task.id} flowId={flowId}/>
        </g>
    </g>

}