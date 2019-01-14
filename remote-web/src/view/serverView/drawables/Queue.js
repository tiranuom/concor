import React from 'react'

export default function ({x, y, color = "#ffffff", queueSize, type = 'ns'}) {
    return <g transform={`translate(${x}, ${y})`}>
        <g transform={'scale(0.5)'}>
            <path d="M 0 15 L 20 30 L 0 45 Z" fill={color} strokeMiterlimit="10" pointerEvents="none"/>
            <rect x="26" y="0" width="8" height="60" fill={color} pointerEvents="none"/>
            <rect x="42" y="0" width="8" height="60" fill={color} pointerEvents="none"/>
            <rect x="58" y="0" width="8" height="60" fill={color} pointerEvents="none"/>
            <path d="M 76 15 L 96 30 L 76 45 Z" fill={color} strokeMiterlimit="10" pointerEvents="none"/>
            {type === 's' && <path d="M 47 80 L 147 210" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/> }
            {type !== 's' && <path d="M 47 80 L -63 210" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/> }
        </g>
        <g transform={type === 's' ? "translate(60,110)" : "translate(-60,110)"}>
            <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="24px" fontFamily="Helvetica" alignmentBaseline={"central"}>{queueSize}
            </text>
        </g>
    </g>
}