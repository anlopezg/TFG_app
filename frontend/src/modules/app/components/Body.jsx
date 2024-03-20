import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout, ViewProfile, BecomesSeller} from '../../users';
import users from '../../users';
import {CreatePattern, ViewAddedPatterns, ViewAddedPhysicals,CreatePhysical, PatternDetails
, ProductDetails, EditPattern, ManagePattern, DeletePattern, EditPhysical, ManagePhysical, DeletePhysical} from "../../publications/index.js";

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const isSeller = useSelector(users.selectors.isSeller);
    const isNormalUser = useSelector(users.selectors.isUser);

   return (

        <div className="container">
            <br/>
            <AppGlobalComponents/>
            <Routes>
                <Route path="/*" element={<Home/>}/>
                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {loggedIn && <Route path="/users/view-profile" element={<ViewProfile/>}/> }
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}

                {loggedIn && isNormalUser && <Route path="/publications/products" element={<BecomesSeller/>}/>}


                {loggedIn && isSeller && <Route path="/publications/create-pattern" element={<CreatePattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/pattern-details/:id" element={<PatternDetails/>}/>}
                {loggedIn && isSeller && <Route path="/publications/patterns" element={<ViewAddedPatterns/>}/>}
                {loggedIn && isSeller && <Route path="/publications/edit-pattern/:id" element={<EditPattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/manage-pattern/:id" element={<ManagePattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/delete-pattern/:id" element={<DeletePattern/>}/>}

                {loggedIn && isSeller && <Route path="/publications/create-physical" element={<CreatePhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/physical-details/:id" element={<ProductDetails/>}/>}
                {loggedIn && isSeller && <Route path="/publications/products" element={<ViewAddedPhysicals/>}/>}
                {loggedIn && isSeller && <Route path="/publications/edit-physical/:id" element={<EditPhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/manage-physical/:id" element={<ManagePhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/delete-physical/:id" element={<DeletePhysical/>}/>}
            </Routes>
        </div>

    );

};

export default Body;
