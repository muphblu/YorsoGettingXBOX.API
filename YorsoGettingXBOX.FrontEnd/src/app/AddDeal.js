import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { browserHistory } from 'react-router';
import { autobind } from 'core-decorators';
import API from './api';

class AddDeal extends Component {
    constructor() {
        super();
        this.handleTouchTap = this.handleTouchTap.bind(this);
    }
    handleTouchTap() {
        let title = this.refs.title.getValue();
        let desc = this.refs.descr.getValue();
        API.addDeal(title, desc).then( ans => browserHistory.push('/deal/'+ans.Id) );
    }
    render() {
        return (<div>
                <h1>Add Deal</h1>
                <TextField
                ref="title"
                hintText="Title"
                /><br />

                <TextField
                ref="descr"
                multiLine={true}
                hintText="Description"
                /><br />

                <RaisedButton label="Create" primary={true} onTouchTap={this.handleTouchTap} />
                </div>
        );
    }
}

export default AddDeal;
