import React from 'react'
import Queue from "./Queue";

export default class SingleThreadedTask extends React.Component {

    onClick(e) {
        console.log(e)
    }

    render() {
        let {x, y, queueSize, latency, itemId} = this.props;

        return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
            <rect x="4" y="30" width="224" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
            <g transform="translate(97.5,-20)">
                <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="18px" fontFamily="Helvetica">{itemId}
                </text>
            </g>

            <g transform="translate(0,220)">
                <path d="M 0 0 L 228 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <path d="M 4 -4 L 04 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <path d="M 224 4 L 224 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <text x="120" y="20" fill="#000000" textAnchor="middle" fontSize="18px" fontFamily="Helvetica" alignmentBaseline={"central"}>{latency}
                </text>
            </g>
            <rect x="73" y="30" width="155" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
            <g transform="translate(-30,-30)">
                <path d="M 0 20 L 18 40 L 290 40 L 310 20" fill="none" stroke="#000000" strokeMiterlimit="10"
                      pointerEvents="none"/>
                <path d="M 0 210 L 20 190 L 290 190 L 310 210" fill="none" stroke="#000000" strokeMiterlimit="10"
                      pointerEvents="none"/>
            </g>
            <Queue x={15} y={70} color={'yellow'} queueSize={queueSize}/>
            <g transform="translate(0,30)">
                <foreignObject>
                    <div onClick={this.onClick.bind(this)} style={{width: 80, height: 110, opacity: 0.1}}></div>
                </foreignObject>
            </g>
        </g>
    }
}