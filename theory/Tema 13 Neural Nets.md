Vamos a ver una pequeña introducción al campo del *Deep Learning*, una reciente rama del Machine Learning que aprovecha la potencia de las GPUs y la plataforma de Cuda para realizar cálculos matriciales masivos.

## Paradigmas del *Machine Learning*
Refiriendonos a como extraemos la esencia (las características) de los datos, por ejemplo, a como reconocemos una cara humana, podemos tener dos enfoques para nuestros modelos:

- ***Shallow Learning***: modelo del machine learning clásico, las características a evaluar son inducidas al modelo por el programador, que a menudo requiere un conocimiento experto del dominio.
- ***Deep Learning***: en el proceso de entrenamiento, el modelo es el que extrae sus propias características del objetivo (a menudo características no distinguibles por los humanos), lo cual logra una mayor precisión en la práctica.

## Conceptos *Deep Learning*

 - **Algoritmo Backpropagation**: algoritmo fundamental para el aprendizaje en redes neuronales, toma los resultados de una evaluación del modelo y los propaga hacia atrás desde el nodo final de la red para el reajuste de los pesos (probar un ajuste más óptimo en la próxima iteración).
- **Gradiente del error**: medida que nos indica cuanto cambia el error total con la modificación del peso sobre el que se aplica, dado que es mayor cuanto más afecte al error final, *se trata de minimizarlo lo máximo posible*.
- **Función de activación**: un nodo supone una combinación lineal sobre los datos de entrada, la función de activación es aplicada al resultado de esta para introducir *no linealidad* al modelo y que este tenga la capacidad de aprender, son comunes *ReLu* (impide valores negativos) o *Sigmoid* (devuelve probabilidades, entre 0 y 1) entre otras.
- **Modelo Dense**: modelo en que las capas están totalmente conectadas, cada output se da a todos los inputs de todos los nodos de la siguiente capa.
- **Overfitting**: situación en que un modelo ha aprendido demasiado bien los casos de ejemplo y ha perdido la capacidad de "extraer los patrones subyacentes al dominio", por lo que no sabe *generalizar* y solo da bien los casos de ejemplo. 

## Una Red Neuronal Simple
Veremos de forma simplificada, los *pasos a seguir* para crear una red neuronal básica.

###### 1. Carga de datos, preprocesado y exploración
A menudo requiere el manejo de grandes datasets y su debido preprocesado y gestión. Una práctica tambien útil es el reconocimiento de los datos, ser conocedor de su tamaño y disposición, dominio y rango de valores para cada atributo.

- Cabe resaltar la técnica de la *normalización* para uniformizar el rango de los datos a valores abarcables como [0, 1].

###### Creación del modelo
Tarea que requiere la creación de todo el modelo, la definición de las características iniciales y todos los algoritmos involucrados (definición de profundidad, tamaño de capa, ...)

###### Fase de entrenamiento
Fase en que al modelo se le pasan los datos objetivo, de los que trata de extraer patrones y clasificar, predecir... Se utiliza un enfoque *iterativo* en que el modelo es entrenado y probado, obteniendo retroalimentación de sus resultados para el reajuste de pesos (hiperparámetros) hasta dar con una solución óptima o suficiente.

-> **Epoch**: número de iteraciones en que el modelo obtiene conclusiones, compara con los datos reales y obtiene retroalimentación.
-> **Batch**: número de muestras para cada actualización de valores (por defecto 32).

## Implementación de una NN simple

``` Python
from tensorflow.keras.datasets import mnist
import tensorflow.keras as keras
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense

# Cargamos los datos (60000, 28, 28) y (10000, 28, 28)
(x_train, y_train), (x_valid, y_valid) = mnist.load_data()

# Hacemos un preprocesado simple (normalización, aplanamiento y categorización)
x_train = x_train/255 # El rango de los datos es 0-255
x_valid = x_valid/255
x_train.reshape(60000, 784)
x_valid.reshape(60000, 784)

num_categories = 10
y_train = keras.utils.to_categorical(y_train, num_categories)
y_valid = keras.utils.to_categorical(y_valid, num_categories)

# Generamos el modelo, capas, tipos, función de activación, métrica...
model = Sequential()
model.add(Dense(units=512, activation="ReLu", input_shape=(784,)))
model.add(Dense(unit=512, activation="ReLu"))
model.add(Dense(unit=10, activation="softmax"))
model.compile(loss="categorical_crossentropy", metrics=['accuracy'])

#Entrenamiento
model.fit(x_train, y_train, epochs=10, validation_data=[x_valid, y_valid])

```


## Redes neuronales Convolucionales (CNNs)
Tipo especial de red neuronal con buen desempeño en análisis de imágenes y visión artificial basadas en *capas de convolución*.

- **Convolución**: #Pendiente


## Data augmentation
#Pendiente

