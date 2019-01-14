import React from 'react'
import Queue from "./Queue";

export default class Start extends React.Component {

    constructor() {
        super();
        this.onClick = this.onClick.bind(this)
    }

    onClick(e) {
        console.log(e)
    }

    render() {

        let {x, y, queueSize, latency, flowId, itemId} = this.props;

        return <g transform={`translate(${x}, ${y}) scale(0.5)`} >
            <ellipse cx="115" cy="85" rx="55" ry="55" fill="#ffffff" stroke="#000000" pointerEvents="none" ref={ref => this.root = ref}/>
            <rect x="115.11" y="30" width="68.89" height="110" fill="#ffffff" stroke="#000000" pointerEvents="none"/>
            <g transform="translate(0.5,78.5)">
                <text x="19" y="12" fill="#000000" textAnchor="middle" fontSize="18px" fontFamily="Helvetica">{flowId}
                </text>
            </g>
            <g transform="translate(101.5,-20)">
                <text x="18" y="12" fill="#000000" textAnchor="middle" fontSize="18px" fontFamily="Helvetica">{itemId}
                </text>
            </g>
            <Queue x={125} y={70} color={'red'} queueSize={queueSize} type={'s'}/>

            <g transform="translate(65,220)">
                <path d="M 0 0 L 120 0" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <path d="M 04 -4 L 04 4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <path d="M 116 4 L 116 -4" fill="none" stroke="#000000" strokeMiterlimit="10" pointerEvents="none"/>
                <text x="60" y="20" fill="#000000" textAnchor="middle" fontSize="18px" fontFamily="Helvetica" alignmentBaseline={"central"}>{latency}
                </text>
            </g>
            <g transform="translate(60,30)">
                <foreignObject>
                    <div onClick={this.onClick.bind(this)} style={{width: 120, height: 120, opacity: 0.1}}></div>
                </foreignObject>
            </g>
        </g>
    }
}