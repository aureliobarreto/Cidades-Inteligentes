# Cidades-Inteligentes
Sistema de redes proposto pela disciplina MI Concorrência e Conectividade do curso de Engenharia de Computação da UEFS em 2018.


Este relatório tem por objetivo exibir detalhadamente a metodologia de
implementação de um produto capaz de gerenciar a coleta de lixo de
uma determinada cidade, no qual cada servidor seria responsável por
administrar uma determinada região da cidade controlando caminhões,
lixeiras e estações de transbordo que enviam dados para esses
servidores.

# Lixeira
Na implementação da lixeira o protocolo de comunicação utilizado foi o UDP.
Contendo um método que recebe uma string, string essa que será a mensagem
enviada ao servidor. Dentro desse método haverá a criação de um datagram (pacote)
com a mensagem, IP e porta onde a mensagem será transformada em um array de
bytes e encaminhada pela rede para o servidor. Esse método é responsável por quase
todo o funcionamento da lixeira que tem por função somente enviar seu estado ao
servidor.
# Caminhão
O caminhão por sua vez utiliza o protocolo de comunicação TCP, porque sua
interface necessita da garantia de entrega das mensagens e isso é uma das principais
vantagens do TCP. O caminhão possui em sua interface uma thread que recebe as
mensagens enviadas pelo servidor como estado das lixeiras de sua rota como também
respostas de solicitações feita na interface.
# Transbordo
O transbordo utiliza o protocolo UDP. Semelhante a lixeira possuí um método de
envio de mensagem através de datagrama avisando somente o seu estado atual
cheio ou vazio.
# Servidor
Por fim, o servidor utiliza quanto o UDP quanto o TCP. Pois, será nele que ocorrerá
todo o gerenciamento dos clientes. Para conhecer os clientes conectados foi
implementado três estruturas de dados do tipo ArrayList para armazenar lixeiras,
caminhões e transbordos conectados. Para facilitar o conhecimento dos clientes foram
utilizadas diferentes portas para cada tipo de operação, na recepção dos clientes 3
porta distintas foram usadas sendo uma para cada tipo de cliente, mais três para
tráfego de mensagens e uma adicional para a enviar os estados das lixeiras para a rota
do caminhão. Além de distribuir portas para cada operação, foram utilizadas threads
específicas para cada tipo de operação como: Recepção, tratamento de mensagens, 
