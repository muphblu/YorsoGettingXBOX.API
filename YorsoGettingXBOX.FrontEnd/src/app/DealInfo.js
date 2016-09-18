import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { browserHistory } from 'react-router';
import { autobind } from 'core-decorators';
import FileDrop from 'react-file-drop';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

import API from './api';

// API.getDeals();

class DocumentRow extends Component {
    constructor() {
        super();
    }

    // @autobind
    // handleTouchTap() {
    //     // API.addDeal();
    //     // browserHistory.push('');
    //     console.log('text', this.refs.title.getValue());
    // }

    render() {
        var styles = {border: "1px solid black", width: 600, color: "black", padding: 20};

        return (
            <li>
                {this.props.info.toString()}
                {/*<RaisedButton label="Create" disabled={this.state.isDocumentAdded} primary={true} onTouchTap={this.handleTouchTap} />*/}
            </li>
        );
    }
}

class DocumentTable extends Component {
    constructor() {
        super();
    }

    @autobind
    sign(id) {
        var self = this;
        API.signDocument(this.props.dealId, id).then( e => {
            if( self.props.onSign ) self.props.onSign();
        });
    }

    render() {
        return (<div>
                <Table>
                <TableHeader>
                <TableRow>
                <TableHeaderColumn>ID</TableHeaderColumn>
                <TableHeaderColumn>Name</TableHeaderColumn>
                <TableHeaderColumn>Sign Status</TableHeaderColumn>
                <TableHeaderColumn>Sign</TableHeaderColumn>
                </TableRow>
                </TableHeader>
                <TableBody>
                {
                    (this.props.documents ?
                        this.props.documents.map( doc => {
                        return  (<TableRow>
                                 <TableRowColumn>{doc.Id}</TableRowColumn>
                                 <TableRowColumn>{doc.Name}</TableRowColumn>
                                 <TableRowColumn><ul>{doc.SignInfo.map( info => {
                                     return (<li> { info.Signer.Name } </li>)
                                 } )}</ul></TableRowColumn>
                                 <TableRowColumn><RaisedButton label="Sign" onTouchTap={ e => this.sign(doc.Id) } /></TableRowColumn>
                                 </TableRow>)
                    }) : (<div/>))
                }
                </TableBody>
                </Table>
                </div>
        );
    }
}

class DocumentUploader extends Component {
    constructor() {
        super();
    }

    // @autobind
    // handleTouchTap() {
    //     // API.addDeal();
    //     // browserHistory.push('');
    //     console.log('text', this.refs.title.getValue());
    // }

    @autobind
    _handleFileDrop(files, event) {
        console.log(files, event);

        let file = files[0];
        let self = this;

        API.postDocument(this.props.dealId, file).then( e => { if(self.props.callback) self.props.callback(); } );
    }

    render() {
        var styles = {border: "1px solid black", width: 600, color: "black", padding: 20};

        return (
            <div className="my-uploader" style={{styles}}>
                Drop some documents here!
                <FileDrop frame={window} onDrop={this._handleFileDrop}>
                    Drop here!
                </FileDrop>
            </div>
        );
    }
}

class DealInfo extends Component {
    constructor() {
        super();

        this.state = {
            dealInfo: null,
            isDocumentAdded: false
        };
    }

    componentWillReceiveProps(nextProps) {
        console.log('3 Get Deal Info by ID:', nextProps.params.dealId);


        API.getDeal(nextProps.params.dealId)
            .then(dealInfo => this.setState({ dealInfo }));
    }

    componentWillMount() {
        console.log('1 Get Deal Info by ID:', this.props.params.dealId);

        API.getDeal(this.props.params.dealId)
            .then(dealInfo => this.setState({ dealInfo }));
    }

    @autobind
    upd(){
        var self = this;
        API.getDeal(this.props.params.dealId)
            .then(dealInfo => self.setState({ dealInfo }));
    }

    render() {
        let dealInfoTemplate = null;

        if (this.state.dealInfo) {
            dealInfoTemplate = (
                <div>
                    <h1>{this.state.dealInfo.Title}</h1>
                    <h3>{this.state.dealInfo.Description}</h3>
                    <DocumentUploader dealId={this.state.dealInfo.Id} callback={ e => { this.upd(); } } />
                    <DocumentTable documents={this.state.dealInfo.Documents} dealId={this.props.params.dealId} onSign={ e => this.upd() } />
                </div>
            );
        } else {
            dealInfoTemplate = <div />;
        }

        return dealInfoTemplate;
    }
}

export default DealInfo;
