import {useElements, useStripe, CardElement, PaymentElement} from "@stripe/react-stripe-js";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import * as actions from '../actions';
import {useState} from "react";
import {Errors} from "../../common/index.js";
import {FormattedMessage} from "react-intl";
import * as selectors from '../selectors.js';
import {Alert} from "react-bootstrap";

const Payment = () =>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const stripe = useStripe();
    const elements = useElements();
    const [backendErrors, setBackendErrors] = useState(null);
    const [paymentMethod, setPaymentMethod] = useState('card');
    const [error, setError] = useState(null);
    let form;

    const lastPurchaseId = useSelector(selectors.getLastPurchaseId);

    console.log("Purchase Id", lastPurchaseId);
    const handlePaymentMethodChange = (event) => {
        setPaymentMethod(event.target.value);
    }


    const handleSubmit = async (event) => {

        event.preventDefault();

        if (!stripe || !elements) {
            return;
        }

        const cardElement = elements.getElement(CardElement);

        try {
            const { paymentMethod, error } = await stripe.createPaymentMethod({
                type: 'card',
                card: cardElement,
            });

            if (error) {
                setError(error.message);
                return;
            }

            const paymentMethodId = paymentMethod.id;
            dispatch(actions.processPaymentForPurchase(lastPurchaseId, paymentMethodId,
                navigate('/shopping/find-purchases-result'),
                errors => setBackendErrors(errors)));

        } catch (error) {
            setError(error.message);
        }
    }

    return (
        <div>
            <Errors errors={backendErrors}
                    onClose={() => setBackendErrors(null)}/>

            <form ref={node => form = node}
                  className="needs-validation" noValidate
                  onSubmit={(e) => handleSubmit(e)}>

                <div className="row justify-content-center">
                    <div className="col-md-6">
                        <div className="card shopping-card my-2 mx-auto">
                            <div className="card-body">
                                <h3 className="retro text-center">
                                    <FormattedMessage id="project.shopping.Purchase.paymentMethod"/>
                                </h3>

                                <p className="small muted text-center it">
                                    <FormattedMessage id="project.shopping.Purchase.paymentMethod.complete"/>
                                </p>

                                <div className="mx-5 mb-4">

                                    <div>
                                        <div className="form-check">
                                            <input className="form-check-input" type="radio" name="paymentMethod" id="card" value="card" checked={paymentMethod === 'card'} onChange={handlePaymentMethodChange}/>
                                            <label className="form-check-label" htmlFor="card">
                                                <FormattedMessage id="project.shopping.Purchase.paymentMethod.complete.card" />
                                            </label>
                                        </div>


                                        {/* Future implementation for other payment methods */}
                                        {/* <div className="form-check">
                                            <input className="form-check-input" type="radio" name="paymentMethod" id="paypal" value="paypal" checked={paymentMethod === 'paypal'} onChange={handlePaymentMethodChange}/>
                                            <label className="form-check-label" htmlFor="paypal">
                                                <FormattedMessage id="project.shopping.Purchase.paymentMethod.paypal" />
                                            </label>
                                        </div> */}
                                    </div>

                                    {paymentMethod === 'card' && (
                                        <div className="mx-5 mb-4 my-4">
                                            <CardElement id="cardElement" className="" />
                                        </div>
                                    )}

                                </div>
                                <br/>

                                <div className="form-group row mb-3">
                                    <div className="col-md-12 d-flex justify-content-center">
                                        <button type="submit" className="btn button-lime">
                                            <FormattedMessage id="project.shopping.BuyForm.title"/>
                                        </button>
                                    </div>
                                </div>

                                {error && <Alert variant="danger">{error}</Alert>}

                            </div>

                        </div>
                    </div>
                </div>

            </form>

        </div>
    )
}

export default Payment;