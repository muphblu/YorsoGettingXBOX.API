import React from 'react';
import {render} from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Main from './Main'; // Our custom react component
import Home from './Home';
import AddDeal from './AddDeal';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import DealInfo from './DealInfo';

// Needed for onTouchTap
// http://stackoverflow.com/a/34015469/988941
injectTapEventPlugin();

// Render the main app react component into the app div.
// For more details see: https://facebook.github.io/react/docs/top-level-api.html#react.render
render(<Router history={browserHistory}>
       <Route path='/' component={Main}>
       <IndexRoute component={Home} />
       <Route path='add' component={AddDeal} />
       <Route path="/deal/:dealId" component={DealInfo}/>
       </Route>
       </Router>, document.getElementById('app'));
