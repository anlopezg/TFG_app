import * as actions from './actions';
import reducer from './reducer';
import * as selectors from './selectors';

export {default as CreatePattern} from './components/CreatePattern.jsx';
export {default as CreatePhysical} from './components/CreatePhysical.jsx';

export {default as ViewAddedPatterns} from './components/ViewAddedPatterns.jsx';
export {default as AddedPatterns} from './components/AddedPatterns.jsx';
export {default as ViewAddedPhysicals} from './components/ViewAddedPhysicals.jsx';
export {default as AddedPhysicals} from './components/AddedPhysicals.jsx';

export {default as PatternDetails} from './components/PatternDetails.jsx';
export {default as PhysicalDetails} from './components/PhysicalDetails.jsx';

export {default as EditPattern} from './components/EditPattern.jsx';
export {default as ManagePattern} from './components/ManagePattern.jsx';
export {default as DeletePattern} from './components/DeletePattern.jsx';

export {default as EditPhysical} from './components/EditPhysical.jsx';
export {default as ManagePhysical} from './components/ManagePhysical.jsx';
export {default as DeletePhysical} from './components/DeletePhysical.jsx';

export default {actions, reducer, selectors};