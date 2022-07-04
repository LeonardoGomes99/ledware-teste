import { useEffect, useState } from 'react';
import { Link,useNavigate } from 'react-router-dom'

import api from '../../services/api';

import Header from '../../components/Header';

import '../Dashboard/dashboard.css';

function Dashboard() {

    const [chamados, setChamados] = useState([]);
    const [status, setStatus] = useState([]);
    const userId = (window.sessionStorage.getItem("usuarioId"));
    const resourceChamado = "/chamado"
    const resourceInteracao = "/interacao"
    const resourceMinIO = "/MinIO"
    const Swal = require('sweetalert2');
    const navigate = useNavigate();



    function deleteChamados(e){
      const chamadoId = e.target.id;
      Swal.fire({
        title: 'Tem certeza que deseja excluir esse chamado?',
        text: "Todas as interações serão removidas junto ao chamado",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: ' #3085d6',
        confirmButtonText: 'Sim, desejo excluir!',
        cancelButtonText: "Cancelar"
      }).then((result) => {
        if (result.isConfirmed) {
          const MinIOStatus = api.delete(`${resourceMinIO}/all/${chamadoId}`);
          const InteracoesStatus = api.delete(`${resourceInteracao}/deleteByChamadoId/${chamadoId}`);
          const ChamadoStatus = api.delete(`${resourceChamado}/${chamadoId}`);

          setChamados(chamados.filter(item => item.id !== chamadoId));
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: ' Chamado removido! ',
            showConfirmButton: false,
            timer: 1500
          })
        
        }        
      })

    }

    useEffect(()=> {

      function sessionCheck() {         
        if(!window.sessionStorage.getItem("usuarioId")){
          navigate("/");
        };
      }

      async function loadChamados(){
        const response = await api.get(`${resourceChamado}/all/${userId}`);

        if(response.data.length > 0){ setChamados(response.data) }else{setChamados([]) };        
      }

      sessionCheck();
      loadChamados();

    }, [] )

    return (
      <div>
        <Header/>
        <div className='dashboard-container'>
          <div className='chamados-container'>
           <Link to='/chamado'><button className='chamados-container-create-chamado'>Criar Chamado</button></Link>
          </div>

          <div className='chamadosTable'>
              <table>
                <tbody>
                  <tr>
                    <th>Assunto</th>
                    <th>Tipo</th>
                    <th>Descrição</th>
                    <th>Data</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                  </tr>
                  {chamados?.map((chamado, index) => {
                    return(
                      <tr key={chamado.id}>
                        <td>{chamado.assunto}</td>
                        <td>{chamado.tipo}</td>
                        <td>{chamado.descricao}</td>
                        <td>{chamado.data}</td>
                        <td className="td-editar"><Link to={`${resourceChamado}/edit/${chamado.id}`} ><button className='table-editar'>Editar</button></Link></td>
                        <td className="td-deletar"><button className='table-deletar' id={chamado.id} name={index} onClick={(e) => deleteChamados(e)} > Deletar </button></td>
                      </tr>
                    )
                  })}  

                </tbody>
              </table>
            </div>
        </div>
      </div>
    );
  }
  
  export default Dashboard;
  