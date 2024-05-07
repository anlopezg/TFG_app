import {init} from './appFetch';
import * as userService from './userService';
import * as publicationService from './publicationService.js';
import * as catalogService from './catalogService.js';
import * as favoriteService from './favoriteService.js';
import * as purchaseService from './purchaseService.js';

export {default as NetworkError} from "./NetworkError";

export default {init, userService, publicationService, catalogService, favoriteService, purchaseService};