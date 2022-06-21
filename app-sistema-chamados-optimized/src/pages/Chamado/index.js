import './Chamado.css'
import Header from '../../components/Header';
import FormChamadoCreate from '../../components/FormChamadoCreate';


export default function Chamado(){
    return(
        <div>
            <Header/>
            <div className='body-chamado-create-form'>
                <FormChamadoCreate/>
            </div>
        </div>
    )
}