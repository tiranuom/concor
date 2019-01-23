import React from "react"
import {Button, FormControl, FormGroup, OverlayTrigger, Popover} from "react-bootstrap";
import ControlLabel from "react-bootstrap/es/ControlLabel";
import {connect} from "react-redux";
import {updateJoin} from "../../../../actions/actions";

class ClickableArea extends React.Component{

    constructor(props) {
        super();
        this.overlayTrigger = null;
        this.state = {
            flowId: props.flowId,
            taskId: props.id,
            threadCount: 10,
            joinType: 'NULL'
        }
    }

    onSubmit() {
        !!this.overlayTrigger && this.overlayTrigger.hide();
        this.props.updateJoin(this.state)
    }

    render() {

        let {id} = this.props;

        const selectionPopover = <Popover id={id} title="Join Selection" >
            <form action={"#"}>
                <FormGroup controlId="joinType">
                    <ControlLabel>Join Type</ControlLabel>
                    <FormControl componentClass="select" placeholder="Join Type"
                                 onChange={event => this.setState({joinType: event.target.value})}
                                 value={this.state.joinType}
                    >
                        <option value={"NULL"}>no Join</option>
                        <option value={"SINGLE_THREADED"}>Single Threaded</option>
                        <option value={"MULTI_THREADED"}>Multi threaded</option>
                        <option value={"CACHED"}>Cached</option>
                    </FormControl>
                </FormGroup>
                {this.state.joinType === 'MULTI_THREADED' &&
                <FormGroup controlId={"threadCount"}>
                    <ControlLabel>Thread Count</ControlLabel>
                    <FormControl
                        type="number"
                        value={this.state.threadCount}
                        placeholder="Enter text"
                        onChange={event => this.setState({threadCount: event.target.value})}
                    />
                </FormGroup>
                }
                <Button onClick={this.onSubmit.bind(this)} bsStyle="success">Ok</Button>
            </form>
        </Popover>

        return <OverlayTrigger trigger={"click"} placement={"bottom"} overlay={selectionPopover} ref={ref => this.overlayTrigger = ref}>
            <div style={{width: 70, height: 110}}></div>
        </OverlayTrigger>;
    }
}

const mapStateToProps = (state, props) => {
    return  {
        ...props,

    }
};
const mapDispatchToProps = dispatch => ({
    updateJoin: (joinData) => dispatch(updateJoin(joinData))
});

export default connect(mapStateToProps, mapDispatchToProps)(ClickableArea)