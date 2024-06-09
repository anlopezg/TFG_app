import * as actionTypes from './actionTypes';
import backend from '../../backend';

const signUpCompleted = authenticatedUser => ({
    type: actionTypes.SIGN_UP_COMPLETED,
    authenticatedUser
});

export const signUp = (user, onSuccess, onErrors, reauthenticationCallback) => dispatch =>
    backend.userService.signUp(user,
        authenticatedUser => {
            dispatch(signUpCompleted(authenticatedUser));
            onSuccess();
        },
        onErrors,
        reauthenticationCallback);

const loginCompleted = authenticatedUser => ({
    type: actionTypes.LOGIN_COMPLETED,
    authenticatedUser
});

export const login = (userName, password, onSuccess, onErrors, reauthenticationCallback) => dispatch =>
    backend.userService.login(userName, password,
        authenticatedUser => {
            dispatch(loginCompleted(authenticatedUser));
            onSuccess();
        },
        onErrors,
        reauthenticationCallback
    );

export const tryLoginFromServiceToken = reauthenticationCallback => dispatch =>
    backend.userService.tryLoginFromServiceToken(
        authenticatedUser => {
            if (authenticatedUser) {
                dispatch(loginCompleted(authenticatedUser));
            }
        },
        reauthenticationCallback
    );
    

export const logout = () => {

    backend.userService.logout();

    return {type: actionTypes.LOGOUT};

};

export const updateProfileCompleted = user => ({
    type: actionTypes.UPDATE_PROFILE_COMPLETED,
    user
})

export const updateProfile = (user, onSuccess, onErrors) => dispatch =>
    backend.userService.updateProfile(user, 
        user => {
            dispatch(updateProfileCompleted(user));
            onSuccess();
        },
        onErrors);



export const changePassword = (id, oldPassword, newPassword, onSuccess, onErrors) => () =>
    backend.userService.changePassword(id, oldPassword, newPassword, onSuccess, onErrors);


const userBecomesSellerCompleted = user =>({
    type: actionTypes.USER_BECOMES_SELLER_COMPLETED,
    user
});

export const userBecomesSeller = ( onSuccess, onErrors) => dispatch =>
    backend.userService.userBecomesSeller(
        user => {
            dispatch(userBecomesSellerCompleted(user));
        },
        onErrors);


const findUserCompleted = user => ({
    type: actionTypes.FIND_USER_COMPLETED,
    user
});

export const findUserByUsername = username => dispatch =>
    backend.userService.findUserByUsername(username ,
        user => dispatch(findUserCompleted(user)));


export const clearUser = () => ({
    type: actionTypes.CLEAR_USER
})




export const createStripeAccountCompleted = accountId =>({
    type: actionTypes.CREATE_STRIPE_ACCOUNT_COMPLETED,
    accountId
});

export const getStripeAccountCompleted = stripeAccount =>({
    type: actionTypes.GET_STRIPE_ACCOUNT_COMPLETED,
    stripeAccount
});

export const getStripeAccountFailure = (error) => ({
    type: actionTypes.GET_STRIPE_ACCOUNT_FAILURE,
    payload: error
});

export const getStripeAccount = (accountId) => dispatch =>

    backend.userService.getStripeAccount(accountId,
        (stripeAccount) => {
            dispatch(getStripeAccountCompleted(stripeAccount));
            },

        (error)=>{
        dispatch(getStripeAccountFailure(error));
        }
    );