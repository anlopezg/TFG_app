import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';


export {default as CreatePhysical} from './components/CreatePhysical.jsx';


export {default as ViewAddedPhysicals} from './components/ViewAddedPhysicals.jsx';
export {default as AddedPhysicals} from './components/AddedPhysicals.jsx';


export {default as PhysicalDetails} from './components/PhysicalDetails.jsx';



export {default as EditPhysical} from './components/EditPhysical.jsx';
export {default as ManagePhysical} from './components/ManagePhysical.jsx';
export {default as DeletePhysical} from './components/DeletePhysical.jsx';

export {default as StoreTabs} from './components/StoreTabs.jsx';

export default {actions, reducer, selectors};