/**
 * In this file, we create a React component
 * which incorporates components provided by Material-UI.
 */
import React, {Component} from 'react';
import RaisedButton from 'material-ui/RaisedButton';

class Home extends Component {
  render() {
    return (<div>
            <h1>Time to start adding deals!</h1>
            <RaisedButton label="Add" href="/add" primary={true} style={{marginTop: 20}} />
            </div>
    );
  }
}

export default Home;
