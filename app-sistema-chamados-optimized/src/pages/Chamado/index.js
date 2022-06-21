import './Chamado.css'
import Header from '../../components/Header';
import FormChamadoCreate from '../../components/FormChamadoCreate';

import ChamadoProvider from '../../contexts/chamado';


export default function Chamado(){
    return(
        <ChamadoProvider>
        <div>
            <Header/>
            <div className='body-chamado-create-form'>
                <FormChamadoCreate/>
            </div>
        </div>
        </ChamadoProvider>
    )
}