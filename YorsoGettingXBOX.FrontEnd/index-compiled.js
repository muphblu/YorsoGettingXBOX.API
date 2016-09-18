/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Created by stinger112 on 17.09.16.
	 */
	
	"use strict";
	
	var _api = __webpack_require__(1);
	
	var _api2 = _interopRequireDefault(_api);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	const api = new _api2.default();

/***/ },
/* 1 */
/***/ function(module, exports) {

	/**
	 * Created by stinger112 on 17.09.16.
	 */
	
	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	
	function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { return step("next", value); }, function (err) { return step("throw", err); }); } } return step("next"); }); }; }
	
	const baseUrl = 'http://localhost:3000';
	
	let API = class API {
	    constructor() {}
	
	    // /api/deals
	    // [
	    //     {
	    //         "ID":12,
	    //         "Title": "title"
	    //         "Description":"desc"
	    //     },
	    //     {
	    //         "ID":12,
	    //         "Title": "title"
	    //         "Description":"desc"
	    //     }
	    // ]
	    getDeals() {
	        return _asyncToGenerator(function* () {
	
	            let body = fetch(`${ baseUrl }/api/deals`);
	            let response = body.json();
	
	            return response;
	        })();
	    }
	};
	exports.default = API;

/***/ }
/******/ ]);
//# sourceMappingURL=index-compiled.js.map