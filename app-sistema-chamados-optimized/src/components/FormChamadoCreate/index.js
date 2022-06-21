import './FormChamadoCreate.css';
import { useContext } from 'react';
import { ChamadoContext } from '../../contexts/chamado';


export default function FormChamadoCreate(){

    const { chamado, setChamado } = useContext(ChamadoContext);

    function chamadoMount(e){
        const { name, value } = e.target;
        setChamado(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function chamadoSave(e){
        e.preventDefault();
        validateFormChamado();
    }

    function validateFormChamado(){
        const thisChamado = chamado;
        if(thisChamado.includes('aer')){
            alert('Deu Nao')
        }
    }

    return(
        <>
            <form>
                <div className='form-inputs'>
                    <label>Assunto</label>
                    <input name="assunto" onChange={(e) => {chamadoMount(e)}} required
                    type='text'
                    ></input>

                    <label>Tipo</label>
                    <input name="tipo" onChange={(e) => {chamadoMount(e)}} required
                    type='text'
                    ></input>

                    <label>Descricao</label>
                    <input name="descricao" onChange={(e) => {chamadoMount(e)}} required
                    type='text'
                    ></input>
                </div>

                <div className='form-buttons'>
                    <button onClick={(e) => chamadoSave(e)}>Cadastrar Chamado</button>
                    <button>Cadastrar Interação</button>
                </div>
            </form>
        </>
    )
}
