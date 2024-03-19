import {init} from './appFetch';
import * as userService from './userService';
import * as publicationService from './publicationService.js';
import * as catalogService from './catalogService.js';

export {default as NetworkError} from "./NetworkError";

export default {init, userService, publicationService, catalogService};