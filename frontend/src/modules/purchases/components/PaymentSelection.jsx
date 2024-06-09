import { useNavigate, useLocation } from 'react-router-dom';

import {FormattedMessage} from "react-intl";


const PaymentSelection = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const { purchasePayment } = location.state;

    const handlePaypalPayment = () =>{
        if (purchasePayment) {
            navigate('/shopping/paypal-payment', {state: {purchasePayment}});
        }else {
            console.error('purchasePayment is undefined or null');
        }
    }

    console.log("Payment Selection: ", purchasePayment);

    return (
        <div className="row justify-content-center my-3">
            <div className="card shopping-card justify-content-center col-md-6">
                <div className="card-body p-5">

                    <h3 className="retro text-center mb-3">
                        <FormattedMessage id="project.shopping.Purchase.paymentMethod"/>
                        <hr/>
                    </h3>

                    <p className="text-center">
                        <FormattedMessage id="project.shopping.Purchase.totalPrice"/>
                        {purchasePayment ? `${purchasePayment.totalPrice}€` : '0€'}
                    </p>


                    <button onClick={handlePaypalPayment} className="btn paypal-button">
                        <img src="https://www.paypalobjects.com/webstatic/icon/pp258.png" alt="PayPal" width="24" height="24" />
                        <FormattedMessage id="project.shopping.Purchase.payPal"/>
                    </button>

                </div>
            </div>
        </div>
    );
};

/*PaymentSelection.propTypes = {
    purchasePayment: PropTypes.object.isRequired
};*/

export default PaymentSelection;
