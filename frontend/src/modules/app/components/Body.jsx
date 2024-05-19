import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout, ViewProfile, BecomesSeller} from '../../users';
import users from '../../users';
import {
    CreatePattern,
    CreatePhysical,
    PatternDetails,
    PhysicalDetails,
    EditPattern,
    ManagePattern,
    DeletePattern,
    EditPhysical,
    ManagePhysical,
    DeletePhysical,
    StoreTabs
} from "../../publications/index.js";

import {FindProductsResult, ProductDetails, FindAllProducts, UserProducts, FindUsersResult} from "../../catalog/index.js";
import {FavoriteList} from "../../favorite/index.js";
import {Buy, ShoppingCart, PurchaseCompleted, FindPurchasesResult, PurchaseDetails} from "../../purchases/index.js";
import {FindUserReviews, EditReview, DeleteReview} from "../../reviews/index.js";

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
                <Route path="/catalog" element={<FindAllProducts/>}/>
                <Route path="/catalog/find-products-result" element={<FindProductsResult/>}/>
                <Route path="/catalog/product-details/:id" element={<ProductDetails/>}/>
                <Route path="/catalog/:username/products" element={<UserProducts/>}/>
                <Route path="/catalog/find-users-result" element={<FindUsersResult/>}/>

                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {loggedIn && <Route path="/users/view-profile" element={<ViewProfile/>}/> }
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}

                {loggedIn && isNormalUser && <Route path="/publications/products" element={<BecomesSeller/>}/>}

                {loggedIn && <Route path="/products/favorites" element={<FavoriteList/>}/>}

                {loggedIn && <Route path="/shopping/cart" element={<ShoppingCart/>}/>}
                {loggedIn && <Route path="/shopping/purchase" element={<Buy/>}/> }
                {loggedIn && <Route path="/shopping/purchase-completed" element={<PurchaseCompleted/>}/> }
                {loggedIn && <Route path="/shopping/find-purchases-result" element={<FindPurchasesResult/>}/> }
                {loggedIn && <Route path="/shopping/purchase-details/:id" element={<PurchaseDetails/>}/>}

                {loggedIn && <Route path="/reviews/find-user-reviews" element={<FindUserReviews/>}/>}
                {loggedIn && <Route path="/reviews/edit-review/:id" element={<EditReview/>}/>}
                {loggedIn && <Route path="/reviews/delete-review/:id" element={<DeleteReview/>}/>}


                {loggedIn && isSeller && <Route path="/publications/create-pattern" element={<CreatePattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/pattern-details/:id" element={<PatternDetails/>}/>}
                {loggedIn && isSeller && <Route path="/publications/patterns" element={<StoreTabs initialTab="patterns"/>}/>}
                {loggedIn && isSeller && <Route path="/publications/edit-pattern/:id" element={<EditPattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/manage-pattern/:id" element={<ManagePattern/>}/>}
                {loggedIn && isSeller && <Route path="/publications/delete-pattern/:id" element={<DeletePattern/>}/>}

                {loggedIn && isSeller && <Route path="/publications/create-physical" element={<CreatePhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/physical-details/:id" element={<PhysicalDetails/>}/>}
                {loggedIn && isSeller && <Route path="/publications/products" element={<StoreTabs initialTab="physicals"/>}/>}
                {loggedIn && isSeller && <Route path="/publications/edit-physical/:id" element={<EditPhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/manage-physical/:id" element={<ManagePhysical/>}/>}
                {loggedIn && isSeller && <Route path="/publications/delete-physical/:id" element={<DeletePhysical/>}/>}
            </Routes>
        </div>

    );

};

export default Body;
