import './FormChamadoCreate.css';


export default function FormChamadoCreate(){
    return(
        <>
            <form>
                <div className='form-inputs'>
                    <label>Assunto</label>
                    <input></input>

                    <label>Tipo</label>
                    <input></input>

                    <label>Descricao</label>
                    <input></input>
                </div>

                <div className='form-buttons'>
                    <button>Cadastrar Chamado</button>
                    <button>Cadastrar Interação</button>
                </div>
            </form>
        </>
    )
}
