import {useDispatch} from "react-redux";
import * as actions from '../actions';
import {useState} from "react";
import {FormattedMessage} from "react-intl";
import {Form, useNavigate} from "react-router-dom";

const UserFilter = () =>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');

    const handleSubmit = event =>{
        event.preventDefault();

        dispatch(actions.findUsers({
            username: username.trim(),
            page:0}));

        dispatch(actions.clearUserSearch());
        setUsername('');

        navigate('/catalog/find-users-result');


    }

    return (
        <div className="">

            <form className="form-wrapper " onSubmit={e => handleSubmit(e)}>
                <div className="input-group rounded">

                    <label htmlFor="username" className="search-placeholder my-1">
                        <FormattedMessage id="project.catalog.Search.ByUsername"/>
                    </label>

                    <input id="username" type="search" className="form-control rounded"
                           aria-label="Search" aria-describedby="search-addon"
                           value={username} onChange={e=> setUsername(e.target.value)}/>

                    <button type="submit" className="btn btn-primary" data-mdb-ripple-init>
                        <i className="fas fa-search"></i>
                    </button>

                </div>

            </form>


        </div>
    )

}

export default UserFilter;